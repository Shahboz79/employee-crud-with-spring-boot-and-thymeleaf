package uz.pdp.springbootexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springbootexample.entity.Attachment;
import uz.pdp.springbootexample.entity.AttachmentContent;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {

}
