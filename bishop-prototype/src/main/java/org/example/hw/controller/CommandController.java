package org.example.hw.controller;

import lombok.RequiredArgsConstructor;
import org.example.synth.core.audit.aspect.WeylandWatchingYou;
import org.example.synth.core.command.model.AndroidCommand;
import org.example.synth.core.command.model.CommandPriority;
import org.example.synth.core.command.service.CommandProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandProcessorService commandProcessorService;

    @PostMapping
    @WeylandWatchingYou
    public ResponseEntity<String> sendCommand(
            @RequestParam String author,
            @RequestParam String description,
            @RequestParam(defaultValue = "COMMON") CommandPriority priority
    ) throws Exception {
        AndroidCommand command = new AndroidCommand();
        command.setAuthor(author);
        command.setDescription(description);
        command.setPriority(priority);
        command.setTime(Instant.now().toString());

        commandProcessorService.handleCommand(command);
        return ResponseEntity.ok("Command accepted");
    }
}
