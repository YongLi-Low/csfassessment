package ibf2022.batch2.csf.backend.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class Uuid {
    
    public String generateBundleId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }
}
