package be.heydari.lib.converters.solr;

import be.heydari.AstWalker;
import be.heydari.ResponseParser;
import be.heydari.ast.ResponseAST;
import be.heydari.lib.expressions.Disjunction;
import internal.org.springframework.content.fragments.SearchableImpl;
import internal.org.springframework.content.solr.SolrFulltextIndexServiceImpl;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.solr.AttributeProvider;
import org.springframework.content.solr.FilterQueryProvider;
import org.springframework.content.solr.SolrConfig;

import javax.persistence.Id;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class SolrUtilsTests {
    private static Logger logger = Logger.getLogger(SolrUtilsTests.class.getName());
    private static final String SOLR_URL = "http://localhost:8983/solr/solr";
    private static final String SOLR_USER = "solr";
    private static final String SOLR_PASSWORD = "SolrRocks";
    private static SolrClient solrClient;
    private static SolrConfig solrConfig = new SolrConfig();
    private static SolrFulltextIndexServiceImpl solrFulltextIndexService;
    private static SearchableImpl searchableSolr;

    @BeforeClass
    public static void init() throws IOException, SolrServerException {
        solrConfig.setUrl(SOLR_URL);
        solrConfig.setUsername(SOLR_USER);
        solrConfig.setPassword(SOLR_PASSWORD);

        solrClient = new HttpSolrClient
                .Builder(SOLR_URL)
                .withConnectionTimeout(5000).withSocketTimeout(3000).build();

        solrFulltextIndexService = new SolrFulltextIndexServiceImpl(solrClient, solrConfig.solrProperties());
        searchableSolr = new SearchableImpl(solrClient, solrConfig.solrProperties());
        searchableSolr.setDomainClass(AccountState.class);

        insertDocuments();
    }

    @AfterClass
    public static void terminate() {
        for (AccountState accountState : getAccountStates()) {
            solrFulltextIndexService.unindex(accountState);
        }
    }

    @Test
    public void testFetchingDocumentsOwnedByBroker() throws IOException {
        // accountStates.broker.id = 1
        Disjunction disjunction = parseAst("1-eq.json");
        String fullTextTerm = "t2";

        searchableSolr.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public String[] filterQueries(Class<?> aClass) {
                return SolrUtils.from(disjunction, "");
            }
        });

        List<Object> searchResultTypeT2ofBrokerId1 = searchableSolr.search(fullTextTerm);

        assertNotNull(searchResultTypeT2ofBrokerId1);
        assertEquals(2, searchResultTypeT2ofBrokerId1.size());
        assertTrue(searchResultTypeT2ofBrokerId1.contains("2"));
        assertTrue(searchResultTypeT2ofBrokerId1.contains("4"));
    }

    @Test
    public void testFetchingDocumentsAll() throws IOException {
        String fullTextTerm = "t2";

        List<Object> searchResultTypeT2 = searchableSolr.search(fullTextTerm);

        assertNotNull(searchResultTypeT2);
        assertEquals(5, searchResultTypeT2.size());
        assertTrue(searchResultTypeT2.contains("2"));
        assertTrue(searchResultTypeT2.contains("4"));
        assertTrue(searchResultTypeT2.contains("3"));
        assertTrue(searchResultTypeT2.contains("6"));
        assertTrue(searchResultTypeT2.contains("7"));
    }

    private static void insertDocuments() throws IOException, SolrServerException {
        for (AccountState accountState : getAccountStates()) {
            solrFulltextIndexService.setAttributeSyncer(new AttributeProvider<AccountState>() {
                @Override
                public Map<String, String> synchronize(AccountState entity) {
                    Map<String,String> attrs = new HashMap<>();
                    attrs.put("broker.id", entity.getBrokerId().toString());
                    return attrs;
                }
            });
            solrFulltextIndexService.index(accountState,
                    IOUtils.toInputStream(String.format("%s %s", accountState.getName(), accountState.getType()), Charsets.UTF_8));
        }
    }

    private static List<AccountState> getAccountStates(){
        List<AccountState> accountStates = new ArrayList<>();
        accountStates.add(new AccountState(10L, "1", "Emad", "t1", 200L));
        accountStates.add(new AccountState(11L, "2", "Paul", "t2", 1L));
        accountStates.add(new AccountState(12L, "3", "Bert", "t2", 56L));
        accountStates.add(new AccountState(13L, "4", "Ronny", "t2", 1L));
        accountStates.add(new AccountState(14L, "5", "Thijs", "t4", 1L));
        accountStates.add(new AccountState(15L, "6", "Toon", "t2", 34L));
        accountStates.add(new AccountState(16L, "7", "Kristof", "t2", 123L));
        return accountStates;
    }

    private Disjunction parseAst(String file) throws IOException {
        ResponseParser responseParser = new ResponseParser();
        ResponseAST responseAST = responseParser.parse(stream(file));
        return AstWalker.walk(responseAST);
    }

    public static InputStream stream(String file) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
    }

    @Data
    @Builder
    static class AccountState {
        @Id
        private Long id;
        @ContentId
        private String contentId;
        private String name;
        private String type;
        private Long brokerId;

        public AccountState() {
        }

        public AccountState(Long id, String contentId, String name, String type, Long brokerId) {
            this.id = id;
            this.contentId = contentId;
            this.name = name;
            this.type = type;
            this.brokerId = brokerId;
        }
    }
}
