package org.acum.exercicespringboot.model.request;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.acum.exercicespringboot.entity.Candidate;

import java.util.List;

@Data
public class CandidateRequest {

    private Candidate candidate;
    private Integer totalPage;
    private Integer totalCount;
    private Integer pageNo;



}