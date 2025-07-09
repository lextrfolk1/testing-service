package com.test.model;

import lombok.Data;

import java.util.Map;

/**
 * Data Transfer Object (DTO) for status metadata.
 * This class holds the essential properties related to a status:
 * - statusCode, statusLabel, statusDescription.
 * - metadataProperties for dynamic properties (e.g., UI hints, restrictions).
 */
@Data
public class StatusMetadataDto {

    /**
     * Unique code representing the status.
     */
    private String statusCode;

    /**
     * Label for the status to be displayed on UI.
     */
    private String statusLabel;

    /**
     * Description of the status, used for tooltips, documentation, etc.
     */
    private String statusDescription;

    /**
     * Dynamic properties related to this status.
     * This is stored as a JSON and parsed into a Map.
     */
    private Map<String, Object> metadataProperties;
}
