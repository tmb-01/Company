package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Worker;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.WorkerDto;
import uz.pdp.company.service.WorkerService;

import java.util.List;

@RestController
@RequestMapping("api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public List<Worker> getAll() {
        return workerService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return workerService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> post(@RequestBody WorkerDto workerDto) {
        return workerService.post(workerDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody WorkerDto workerDto) {
        return workerService.update(id, workerDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return workerService.delete(id);
    }
}
