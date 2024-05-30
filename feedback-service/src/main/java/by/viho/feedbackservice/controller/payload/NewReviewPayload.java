package by.viho.feedbackservice.controller.payload;

public record NewReviewPayload(int itemId, int rating, String review)
{
}
