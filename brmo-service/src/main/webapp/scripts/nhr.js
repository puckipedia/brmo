/*
 * Copyright (C) 2011-2014 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Berichten grid
 */
Ext.define('B3P.brmo.NHRNummers', {
    constructor: function(config) {
        Ext.onReady(function() {
            // Create the grid
            var gridSelection = Ext.create('B3P.common.GridSelection', {
                fields: [
                    {name: 'id', type: 'int'},
                    {name: 'datum', type: 'string'},
                    {name: 'laatstGeprobeerd', type: 'string'},
                    {name: 'volgendProberen', type: 'string'},
                    {name: 'probeerAantal', type: 'int'},
                    {name: 'kvkNummer', type: 'string'}
                ],
                columns: [
                    {text: "id", dataIndex: 'id'},
                    {text: "datum", dataIndex: 'datum'},
                    {text: "datum", dataIndex: 'datum'},
                    {text: 'laatst geprobeerd', dataIndex: 'laatstGeprobeerd'},
                    {text: 'volgend proberen', dataIndex: 'volgendProberen'},
                    {text: "probeerAantal", dataIndex: 'probeerAantal'},
                    {text: "kvkNummer", dataIndex: 'kvkNummer'},
                ],
                gridUrl: config.gridurl,
                gridId: 'nhr-grid',
                commentId: 'comment-div'
            });

            Ext.create('Ext.Button', {
                text: 'Vestigingen nu opnieuw ophalen',
                renderTo: 'button-retry',
                handler: function() {
                    var ids = gridSelection.grid.getSelection();
                    if(ids.length === 0) {
                        Ext.Msg.alert('Vestigingen ophalen', 'Geen vestigingen geselecteerd!');
                        return;
                    }

                    var p = "";
                    for(var i = 0; i < ids.length; i++) {
                        if(p !== "") {
                            p += "&";
                        }
                        p += "selectedIds=" + ids[i].id;
                    }

                    Ext.Ajax.request({
                        url: config.runurl + "&" + p,
                        callback: function(options, success, response) {
                            Ext.Msg.alert('Vestigingen ophalen', response.responseText);
                            gridSelection.reloadGrid();
                        }
                    });
                }
            });
        });
    }
});
