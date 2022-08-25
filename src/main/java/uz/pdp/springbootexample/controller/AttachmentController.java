package uz.pdp.springbootexample.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.springbootexample.entity.AttachmentContent;
import uz.pdp.springbootexample.repository.AttachmentContentRepository;


@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentContentRepository attachmentContentRepo;

    public AttachmentController(AttachmentContentRepository attachmentContentRepo) {
        this.attachmentContentRepo = attachmentContentRepo;
    }


    @GetMapping("/download/{attachmentId}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer attachmentId) {
        AttachmentContent attachmentContent = attachmentContentRepo.findByAttachmentId(attachmentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachmentContent.getAttachment().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + attachmentContent.getAttachment().getFileOriginalName() + "\"")
                .body(new ByteArrayResource(attachmentContent.getMainContent()));
    }

}
