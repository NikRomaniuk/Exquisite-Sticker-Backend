package io.github.nikromaniuk.exquisite_sticker_backend.dto;

import java.util.UUID;

public record StickerDto
(
        UUID id,
        String title,
        String imageUrl,
        int positionX,
        int positionY
) {}
