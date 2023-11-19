package com.example.Social.Network.API.Model.ReqDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchReqDto  implements Serializable {

    @Column(name = "count")
    private Integer count;

    @Max(value = 100, message = "maxium value is 100")
    @Column(name = "size")
    private Integer size = 10;

    @Column(name = "sort_by")
    private String sortBy;
    @Column(name = "index")
    private String index = "asc";
}
