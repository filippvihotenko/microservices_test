package by.viho.managerapp.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewItemPayload( String title,String details)
{
}
