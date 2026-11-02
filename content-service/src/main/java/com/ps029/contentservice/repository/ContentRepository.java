
package com.ps029.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps029.contentservice.entity.Content;

@Repository
public interface ContentRepository
        extends JpaRepository<Content, Long> {

}
