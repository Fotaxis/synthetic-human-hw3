package org.example.synth.core.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
public class AndroidCommand {
    @NotBlank(message = "Author is required")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "Priority is required")
    private CommandPriority priority;

    @NotBlank(message = "Description is required")
    @Size(max = 100)
    private String author;

    @NotNull(message = "Time is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*")
    private String time;

}
