/*
 * Copyright (C) 2021 B3Partners B.V.
 *
 * SPDX-License-Identifier: MIT
 */

package nl.b3p.brmo.bgt.download.api;

import nl.b3p.brmo.bgt.download.model.DeltaCustomDownloadStatusResponse;

import java.time.Instant;

public class CustomDownloadProgress {
    String downloadRequestId;
    Instant start;
    int statusApiCalls = 0;
    String timeSinceStart;
    DeltaCustomDownloadStatusResponse statusResponse;

    public String getDownloadRequestId() {
        return downloadRequestId;
    }

    public Instant getStart() {
        return start;
    }

    public int getStatusApiCalls() {
        return statusApiCalls;
    }

    public String getTimeSinceStart() {
        return timeSinceStart;
    }

    public DeltaCustomDownloadStatusResponse getStatusResponse() {
        return statusResponse;
    }
}
