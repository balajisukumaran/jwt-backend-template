package com.authtemplate.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * reset data transfer object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetDto {
    /**
     * string flag
     */
    String flag;
}
