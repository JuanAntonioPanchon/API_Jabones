package com.dwes.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.dwes.api.entidades.Jabon;
import com.dwes.api.errores.JabonNotFoundException;
import com.dwes.api.repositorios.JabonRepository;
import com.dwes.api.servicios.impl.JabonServiceImpl;

@ExtendWith(MockitoExtension.class) // Habilita Mockito en JUnit 5
class JabonServiceTest {

    @Mock
    private JabonRepository jabonRepository; // Simula la base de datos

    @Mock
    private RestTemplate restTemplate; // Simula la API de clima

    @InjectMocks
    private JabonServiceImpl jabonService; // Servicio real con dependencias simuladas

    @Test
    void testObtenerClimaDeJabon_FuncionaOK() {
        // Arrange - Configurar datos simulados
        Jabon jabon = new Jabon();
        jabon.setId(1L);
        jabon.setNombre("Jabón de Lavanda");
        jabon.setCiudad("Madrid");

        when(jabonRepository.findById(1L)).thenReturn(Optional.of(jabon));

        // Simula la respuesta de la API externa
        String apiResponse = "{ \"location\": { \"name\": \"Madrid\" }, \"current\": { \"temp_c\": 22.5 } }";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(apiResponse);

        // Act - Ejecutar la prueba
        String clima = jabonService.getClima(1L);

        // Assert - Verificar que la respuesta es correcta
        assertNotNull(clima);
        assertTrue(clima.contains("Madrid"));
        assertTrue(clima.contains("22.5"));
    }

    @Test
    void testObtenerClimaDeJabon_NotFound() {
        // Arrange - Simula que el jabón no existe
        when(jabonRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert - Verifica que se lanza una excepción
        assertThrows(JabonNotFoundException.class, () -> {
            jabonService.getClima(99L);
        });
    }
}
