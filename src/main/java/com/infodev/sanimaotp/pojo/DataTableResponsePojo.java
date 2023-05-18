package com.infodev.sanimaotp.pojo;

import lombok.*;

import java.util.List;

/**
 * @author ablok.shakya
 * @created 1/17/2023
 * @project sanimaotp
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataTableResponsePojo{
    /** The draw. */
    private String draw;

    /** The records filtered. */
    private String recordsFiltered;

    /** The records total. */
    private String recordsTotal;

    /** The total pages. */
     private String pages;

    /** The list of data objects. */
    List data;
}
