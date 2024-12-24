package ru.platform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "options", schema = "dev")
@Schema(description = "Объект в котором хранятся опции со стоимостями для сервисов")
public class OptionsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID опции")
    private UUID id;

    @Column(name = "type")
    @Schema(description = "Тип опции (select, slider, checkbox ...)")
    String type;

    @Column(name = "options", columnDefinition="TEXT")
    @Schema(description = "Опции (названия-стоимость)")
    String options;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServicesEntity service;
}
