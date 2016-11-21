package dk.cngroup.intranet.locator.services;
import dk.cngroup.intranet.locator.services.storage.StorageFileNotFoundException;
import dk.cngroup.intranet.locator.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Collectors;

/**
 * EmployeesController will provide REST services to handle user upload of files
 * @author Victor Cano
 */
@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Rest service to list uploaded files a floor in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    /**
     * Rest service to load a file and serve it
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    /**
     * Rest service to upload an image file in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @PostMapping(path="/files/upload", headers=("content-type=multipart/*"), consumes = "image/jpeg")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        Path movefrom = storageService.loadFromTemp(file.getOriginalFilename());
        Path target = storageService.load(file.getOriginalFilename());

        try {
            Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    public String deleteFileFromFloor(String filename){
        try {
            Path target = storageService.load(filename);
            Files.delete(target);
        }catch (IOException e) {
            System.err.println(e);
        }

        return "Done";
    }

}