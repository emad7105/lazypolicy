package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefTermValue implements TermValue<List<RefTermValuePart>> {

    private List<RefTermValuePart> refTermValueParts;

    public static RefTermValue fromData(JsonNode refTermValueParts) {
        List<RefTermValuePart> refTermValuePartList = new ArrayList<>();
        for (JsonNode refTermValuePart : refTermValueParts) {
            refTermValuePartList.add(RefTermValuePart.fromData(refTermValuePart));
        }

        return RefTermValue.builder()
                .refTermValueParts(refTermValuePartList)
                .build();
    }



    @Override
    public List<RefTermValuePart> getValue() {
        return refTermValueParts;
    }
}
