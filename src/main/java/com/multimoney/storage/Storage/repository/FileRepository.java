package com.multimoney.storage.Storage.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.multimoney.storage.Storage.model.File; 

@Repository
public interface FileRepository  extends JpaRepository<File, String> {
    
}
