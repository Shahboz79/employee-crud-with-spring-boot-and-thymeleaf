package uz.pdp.springbootexample.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.springbootexample.entity.Attachment;
import uz.pdp.springbootexample.entity.AttachmentContent;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.repository.AttachmentContentRepository;
import uz.pdp.springbootexample.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    public void uploadFile(Employee employee, MultipartFile file) throws IOException {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();

            Attachment attachment = new Attachment();

            attachment.setFileOriginalName(originalFilename);
            attachment.setContentType(contentType);
            attachment.setSize(size);
            attachment.setEmployee(employee);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(attachment);
            attachmentContent.setMainContent(file.getBytes());

            attachmentContentRepository.save(attachmentContent);
        }
    }
//    public void getFileFromDb(Integer id, HttpServletResponse response) throws IOException {
//
//        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
//        if (optionalAttachment.isPresent()) {
//            Attachment attachment = optionalAttachment.get();
//
//            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findById(id);
//            if (optionalAttachmentContent.isPresent()) {
//                AttachmentContent attachmentContent = optionalAttachmentContent.get();
//
//                response.setHeader("Content-Disposition",
//                        "attachment; fileName=\""+attachment.getFileOriginalName()+"\"");
//                response.setContentType(attachment.getContentType());
//                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
//            }
//        }
//    }
}
