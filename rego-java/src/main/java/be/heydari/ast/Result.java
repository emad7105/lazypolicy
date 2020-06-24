
package be.heydari.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "queries",
    "support"
})
public class Result {

    @JsonProperty("queries")
    private List<List<Query>> queries = null;
    @JsonProperty("support")
    private List<Support> support = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("queries")
    public List<List<Query>> getQueries() {
        return queries;
    }

    @JsonProperty("queries")
    public void setQueries(List<List<Query>> queries) {
        this.queries = queries;
    }

    @JsonProperty("support")
    public List<Support> getSupport() {
        return support;
    }

    @JsonProperty("support")
    public void setSupport(List<Support> support) {
        this.support = support;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
