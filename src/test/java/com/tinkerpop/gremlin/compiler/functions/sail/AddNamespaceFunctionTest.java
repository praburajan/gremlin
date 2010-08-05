package com.tinkerpop.gremlin.compiler.functions.sail;

import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.impls.sail.SailGraph;
import com.tinkerpop.gremlin.BaseTest;
import com.tinkerpop.gremlin.compiler.types.Atom;
import com.tinkerpop.gremlin.compiler.Tokens;
import com.tinkerpop.gremlin.compiler.context.GremlinScriptContext;
import com.tinkerpop.gremlin.compiler.functions.Function;
import org.openrdf.sail.memory.MemoryStore;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class AddNamespaceFunctionTest extends BaseTest {

    public void testAddNamespace() {
        SailGraph graph = new SailGraph(new MemoryStore());
        GremlinScriptContext context = new GremlinScriptContext();
        context.getVariableLibrary().declare(Tokens.GRAPH_VARIABLE, new Atom<Graph>(graph));

        Function<Boolean> function = new AddNamespaceFunction();
        this.stopWatch();
        Atom<Boolean> atom = function.compute(createUnaryArgs(graph, "tp", "http://tinkerpop.com"), context);
        printPerformance(function.getFunctionName() + " function", 1, "namespace added", this.stopWatch());
        assertTrue(atom.getValue());
        assertEquals(graph.getNamespaces().get("tp"), "http://tinkerpop.com");

        this.stopWatch();
        atom = function.compute(createUnaryArgs("marko", "http://markorodriguez.com"), context);
        printPerformance(function.getFunctionName() + " function", 1, "namespace added", this.stopWatch());
        assertTrue(atom.getValue());
        assertEquals(graph.getNamespaces().get("marko"), "http://markorodriguez.com");
        assertEquals(graph.getNamespaces().get("tp"), "http://tinkerpop.com");

        graph.shutdown();
    }
}