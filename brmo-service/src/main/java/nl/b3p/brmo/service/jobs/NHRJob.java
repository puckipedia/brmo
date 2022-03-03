/*
 * Copyright (C) 2022 B3Partners B.V.
 */
package nl.b3p.brmo.service.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.nhr.loader.cli.NHRLoadUtils;
import nl.b3p.brmo.nhr.loader.NHRCertificateOptions;
import nl.b3p.brmo.nhr.loader.NHRLoader;
import nl.b3p.brmo.persistence.staging.NHRLaadProces;
import nl.b3p.brmo.service.util.ConfigUtil;
import nl.kvk.schemas.schemas.hrip.dataservice._2015._02.Dataservice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stripesstuff.stripersist.Stripersist;

@DisallowConcurrentExecution
public class NHRJob implements Job {

    private static final Log log = LogFactory.getLog(NHRJob.class);

    private boolean fetchThing(Dataservice dataservice, String kvkNummer) {
        BrmoFramework brmo = null;
        try {
            DataSource dataSourceStaging = ConfigUtil.getDataSourceStaging();
            brmo = new BrmoFramework(dataSourceStaging, null, null, null);

            brmo.setOrderBerichten(false);

            NHRLoader.sendSingleRequest(dataservice, brmo, kvkNummer, null);
            return true;
        } catch (Exception e) {
            log.error("failed", e);
        } finally {
            if (brmo != null) {
                brmo.closeBrmoFramework();
            }
        }

        return false;
    }

    private Dataservice getDataservice() {
        try {
            InitialContext ctx = new InitialContext();
            Boolean isActive = (Boolean) ctx.lookup("java:comp/env/brmo/nhr/active");
            if (!isActive) {
                return null;
            }

            NHRCertificateOptions certOptions = new NHRCertificateOptions();
            certOptions.setKeystore((String) ctx.lookup("java:comp/env/brmo/nhr/keystorePath"));
            certOptions.setKeystorePassword((String) ctx.lookup("java:comp/env/brmo/nhr/keystorePassword"));
            certOptions.setKeystoreAlias("key");
            certOptions.setTruststore((String) ctx.lookup("java:comp/env/brmo/nhr/truststorePath"));
            certOptions.setTruststorePassword((String) ctx.lookup("java:comp/env/brmo/nhr/truststorePassword"));

            String endpoint = (String) ctx.lookup("java:comp/env/brmo/nhr/endpoint");
            boolean isPreprod = (Boolean) ctx.lookup("java:comp/env/brmo/nhr/endpointIsPreprod");

            return NHRLoadUtils.getDataservice(endpoint, isPreprod, certOptions);
        } catch (Exception e) {
            throw new Error("Failed to initialize NHR DataService", e);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Dataservice ds = getDataservice();
        if (ds == null) {
            return;
        }

        while (true) {
            // Batch requests in groups of 100, to make sure the database doesn't end up too far behind,
            // and the user interface stays roughly up to date.
            Stripersist.requestInit();
            EntityManager entityManager = Stripersist.getEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<NHRLaadProces> cq = cb.createQuery(NHRLaadProces.class);
            Root<NHRLaadProces> from = cq.from(NHRLaadProces.class);
            cq.where(cb.lessThan(from.get("volgendProberen"), cb.currentTimestamp()));
            List<NHRLaadProces> procList = entityManager.createQuery(cq).setMaxResults(100).getResultList();
            if (procList.isEmpty()) break;

            log.info(String.format("processing %d items", procList.size()));

            for (NHRLaadProces process : procList) {
                boolean fail = false;

                fail = !fetchThing(ds, process.getKvkNummer());

                if (fail) {
                    log.error(String.format("fetching KVK nummer %s (id %d) failed (%d times so far)", process.getKvkNummer(), process.getId(), process.getProbeerAantal()));
                    process.setProbeerAantal(process.getProbeerAantal() + 1);
                    Calendar time = Calendar.getInstance();
                    process.setLaatstGeprobeerd(new Date());
                    // Wait for 8 seconds, then 16, then 32, etc
                    time.add(Calendar.SECOND, (int) Math.pow(2, 2 + process.getProbeerAantal()));
                    process.setVolgendProberen(time.getTime());
                    entityManager.merge(process);
                } else {
                    entityManager.remove(process);
                }
            }

            entityManager.flush();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().commit();
                entityManager.getTransaction().begin();
            }
        }

        Stripersist.requestComplete();
    }
}
