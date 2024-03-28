package org.acum.exercicespringboot.specification;

import jakarta.persistence.criteria.Predicate;
import org.acum.exercicespringboot.entity.Candidate;
import org.acum.exercicespringboot.model.request.CandidateRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CandidateSpecification {

        public Specification<Candidate> getCandidate(CandidateRequest request) {
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(request.getCandidate().getEmail())) {
                     predicates.add(criteriaBuilder.like(root.get("email"),
                            "%" + request.getCandidate().getEmail().toLowerCase().trim() + "%"));
                }
                if (!StringUtils.isEmpty(request.getCandidate().getFnameen())) {
                              predicates.add(criteriaBuilder.like(root.get("fnameen"),
                             "%" + request.getCandidate().getFnameen().toLowerCase().trim()  + "%"));
                 }

                if (!StringUtils.isEmpty(request.getCandidate().getLnameen())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lnameen")), request.getCandidate().getLnameen()));
                }
                if (!StringUtils.isEmpty(request.getCandidate().getGender())) {
                    predicates.add(criteriaBuilder.equal(root.get("gender"), request.getCandidate().getGender()));
                 }
                if (!StringUtils.isEmpty(request.getCandidate().getIdnumber())) {
                    predicates.add(criteriaBuilder.equal(root.get("idnumber"), request.getCandidate().getIdnumber()));
                }
                 if (!StringUtils.isEmpty(request.getCandidate().getUsername())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")),
                            "%" + request.getCandidate().getUsername().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(request.getCandidate().getPhone())) {
                    predicates.add(criteriaBuilder.equal(root.get("phone"), request.getCandidate().getPhone()));
                }
                if (!StringUtils.isEmpty(request.getCandidate().getMobile())) {
                    predicates.add(criteriaBuilder.like(root.get("mobile"), request.getCandidate().getMobile()));
                }
                if (!StringUtils.isEmpty(request.getCandidate().getCitycode())) {
                    predicates.add(criteriaBuilder.equal(root.get("citycode"), request.getCandidate().getCitycode()));
                }
                query.orderBy(criteriaBuilder.desc(root.get("key")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }

