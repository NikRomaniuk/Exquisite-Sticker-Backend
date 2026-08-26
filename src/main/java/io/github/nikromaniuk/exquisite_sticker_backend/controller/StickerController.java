package io.github.nikromaniuk.exquisite_sticker_backend.controller;

import io.github.nikromaniuk.exquisite_sticker_backend.dto.StickerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stickers")
@CrossOrigin(origins = "*") // Allow requests from anywhere
public class StickerController
{
    // Mock list
    private final List<StickerDto> stickers = new ArrayList<>(List.of(
            new StickerDto(UUID.randomUUID(), "Leaf", "https://example.com/cat.png", 100, 150),
            new StickerDto(UUID.randomUUID(), "Rock", "https://example.com/cat.png", 50, 300)
    ));

    // GET /api/v1/stickers
    @GetMapping
    public List<StickerDto> getAllStickers()
    {
        return stickers;
    }

    // GET /api/v1/stickers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<StickerDto> getStickerById(@PathVariable UUID id)
    {
        return stickers.stream()
                .filter(s -> s.id().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    // POST /api/v1/stickers
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public StickerDto createSticker(@RequestBody StickerDto dto)
    {
        StickerDto newSticker = new StickerDto(
                UUID.randomUUID(),
                dto.title(),
                dto.imageUrl(),
                dto.positionX(),
                dto.positionY()
        );

        stickers.add(newSticker);
        return newSticker;
    }

    // PATCH /api/v1/stickers/{id}/position
    @PatchMapping("/{id}/position")
    public ResponseEntity<StickerDto> updatePosition(
            @PathVariable UUID id,
            @RequestParam int x,
            @RequestParam int y
    )
    {
        for (int i = 0; i < stickers.size(); i++)
        {
            StickerDto current = stickers.get(i);
            if (current.id().equals(id))
            {
                StickerDto updated = new StickerDto(current.id(), current.title(), current.imageUrl(), x, y);
                stickers.set(i, updated);
                return ResponseEntity.ok(updated);
            }
        }
        return ResponseEntity.notFound().build(); // 404
    }

    // DELETE /api/v1/stickers/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteSticker(@PathVariable UUID id)
    {
        stickers.removeIf(s -> s.id().equals(id));
    }
}
