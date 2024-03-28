package org.acum.exercicespringboot.service;


import org.acum.exercicespringboot.entity.Candidate;

import org.acum.exercicespringboot.model.request.CandidateRequest;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.logging.Logger;

public interface ICandidateService {
     Page<Candidate> findAll(Pageable Page, Candidate search);

     Page<Candidate> getListCandidate(PageRequest of, CandidateRequest request);

     Page<Candidate> findAll(Specification<Candidate> search,Pageable Page);

     Page<Candidate> findAll(Pageable Page);

     Optional<Candidate> findById(Integer key);

     Candidate updateCandidate( Candidate candidate );

     void clearCache();
}
