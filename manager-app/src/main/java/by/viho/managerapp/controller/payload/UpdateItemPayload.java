package by.viho.managerapp.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateItemPayload(String title,  String details)
{
}
