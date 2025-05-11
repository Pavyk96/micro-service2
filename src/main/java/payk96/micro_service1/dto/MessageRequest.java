package payk96.micro_service1.dto;

public record MessageRequest(
        String name,
        String text,
        Boolean flag
) {
}
