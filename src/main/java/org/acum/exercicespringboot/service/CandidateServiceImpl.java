package org.acum.exercicespringboot.service;

import org.acum.exercicespringboot.dao.CandidateRepository;
import org.acum.exercicespringboot.entity.Candidate;

import org.acum.exercicespringboot.model.request.CandidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class CandidateServiceImpl implements ICandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    private final Logger LOG= LoggerFactory.getLogger(CandidateServiceImpl.class);

    @Override
    public Page<Candidate> findAll(Pageable Page, Candidate search) {
        return candidateRepository.findAll(Page);
    }

    @Override
    public Page<Candidate> getListCandidate(PageRequest of, CandidateRequest request) {
        return null;
    }

    @Override
    public Page<Candidate> findAll(Specification<Candidate> search, Pageable Page) {
        return candidateRepository.findAll(search,Page);}

     @Override
     @Cacheable(value="candidate" )
    public Page<Candidate> findAll(Pageable Page) {
        LOG.info("findAll Candidate");
        return candidateRepository.findAll(Page);}

   // @Cacheable (cacheNames = "candidate"}, key="#id")

    @Override
   @Cacheable(value="candidate",key="#key")
     public Optional<Candidate> findById(Integer key) {
        LOG.info("** Find Candidate By Id :"+key);
         return candidateRepository.findById(key);
    }

    @Override
    @CachePut  (cacheNames = "candidate", key="#candidate.key")
    public Candidate updateCandidate(Candidate candidate) {
        LOG.info("updateCandidate  Fnameen "+candidate.getFnameen());
        return candidateRepository.save(candidate);
    }

 @CacheEvict(cacheNames = {"candidate"}, allEntries = true)
    @Override
    public void clearCache() {
        LOG.info("clear Cache...");

    }


}







