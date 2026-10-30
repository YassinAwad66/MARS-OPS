package resources;
import core.OperationResult;
import facilities.*;
import java.util.*;

public class ResourcePool {
    private final List<Facility> facilities;

    public ResourcePool(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public int getTotal(ResourceType t) {
        int total = 0;
        for (Facility f : facilities) {
            if (f.isOperational()) {
                total += f.getQuantity(t);
            }
        }
        return total;
    }

    public OperationResult canSupply(Map<ResourceType, Integer> req) {
        for (Map.Entry<ResourceType, Integer> e : req.entrySet()) {
            if (getTotal(e.getKey()) < e.getValue()) {
                return OperationResult.fail("Not enough " + e.getKey());
            }
        }
        return OperationResult.success("OK");
    }

    public OperationResult consume(Map<ResourceType, Integer> req) {
        OperationResult check = canSupply(req);
        if (!check.isSuccess())
             return check;

        for (Map.Entry<ResourceType, Integer> e : req.entrySet()) {
            int remaining = e.getValue();
            for (Facility f : orderedFacilities(e.getKey())) {
                if (remaining == 0) break;
                int take = Math.min(remaining, f.getQuantity(e.getKey()));
                if (take > 0 && f.removeResource(e.getKey(), take)) {
                    remaining -= take;
                }
            }
        }
        return OperationResult.success("Consumed");
    }

    private List<Facility> orderedFacilities(ResourceType t) {
        List<Facility> result = new ArrayList<>();
        for (Facility f : facilities) {
            if (f.isOperational() && f.getQuantity(t) > 0) {
                result.add(f);
            }
        }
        result.sort((a, b) -> b.getQuantity(t) - a.getQuantity(t));
        return result;
    }
}