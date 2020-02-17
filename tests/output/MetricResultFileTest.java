package output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import general.ProjectInfo;

public class MetricResultFileTest {
	private static final String PROJECT_DIRECTORY = "./resources/javaProject/";
	private static final String SAVED_FILES = "./resources/files/";
	private static final String SUMMARY_INFO = SAVED_FILES + "drtools-metric-summary.csv";
	private static final String NAMESPACES_INFO = SAVED_FILES + "drtools-metric-namespaces.csv";
	private static final String TYPES_INFO = SAVED_FILES + "drtools-metric-types.csv";
	private static final String METHODS_INFO = SAVED_FILES + "drtools-metric-methods.csv";
	private static final String NAMESPACE_COUPLING_INFO = SAVED_FILES + "drtools-metric-namespace-coupling.csv";
	private static final String INTERNAL_DEPENDENCIES_INFO = SAVED_FILES + "drtools-metric-internal-dependencies.json";
	private static final String CYCLIC_DEPENDENCIES_INFO = SAVED_FILES +"drtools-metric-cyclic-dependencies.csv";
	private static final String METRIC_THRESHOLDS_INFO = SAVED_FILES + "drtools-metric-thresholds.csv";
	private static final String TYPE_COUPLING_INFO = SAVED_FILES + "drtools-metric-type-coupling.csv";
	private static final String TYPE_RESONANCE_INFO = SAVED_FILES + "drtools-metric-resonance.json";

	private static ProjectInfo projectInfo;
	private static MetricResultFile file;
	private static MetricResultCSV csv;
	private static MetricResultJSON json;
	
	@BeforeClass
	public static void setUp() {
		deleteFiles();
		file = new MetricResultFile();
		csv = new MetricResultCSV();
		json = new MetricResultJSON();
		projectInfo = new ProjectInfo(PROJECT_DIRECTORY, file);
		projectInfo.analyze();
		projectInfo.show("-mv");
		csv.setResults(projectInfo.getNamespaceMetricResult(), 
				projectInfo.getTypeMetricResult(), projectInfo.getMethodMetricResult());
		json.setResults(projectInfo.getNamespaceMetricResult(), 
				projectInfo.getTypeMetricResult(), projectInfo.getMethodMetricResult());
	}

	private static void deleteFiles() {
		new File(SUMMARY_INFO).delete();
		new File(NAMESPACES_INFO).delete();
		new File(TYPES_INFO).delete();
		new File(METHODS_INFO).delete();
		new File(NAMESPACE_COUPLING_INFO).delete();
		new File(INTERNAL_DEPENDENCIES_INFO).delete();
		new File(CYCLIC_DEPENDENCIES_INFO).delete();
		new File(METRIC_THRESHOLDS_INFO).delete();
		new File(TYPE_COUPLING_INFO).delete();
		new File(TYPE_RESONANCE_INFO).delete();
	}

	@Test
	public void testForSummaryFile() {
		assertTrue(file.generateSummaryFile(SUMMARY_INFO));
		assertTrue(new File(SUMMARY_INFO).exists());
		assertEquals(csv.generateSummary(), readStringFrom(SUMMARY_INFO));
	}

	@Test
	public void testForNamespacesFile() {
		assertTrue(file.generateNamespacesFile(NAMESPACES_INFO));
		assertTrue(new File(NAMESPACES_INFO).exists());
		assertEquals(csv.generateNamespaces(), readStringFrom(NAMESPACES_INFO));
	}

	@Test
	public void testForTypesFile() {
		assertTrue(file.generateTypesFile(TYPES_INFO));
		assertTrue(new File(TYPES_INFO).exists());
		assertEquals(csv.generateTypes(), readStringFrom(TYPES_INFO));
	}

	@Test
	public void testForMethodsFile() {
		assertTrue(file.generateMethodsFile(METHODS_INFO));
		assertTrue(new File(METHODS_INFO).exists());
		assertEquals(csv.generateMethods(), readStringFrom(METHODS_INFO));
	}

	@Test
	public void testForNamespaceCouplingFile() {
		assertTrue(file.generateNamespaceCouplingFile(NAMESPACE_COUPLING_INFO));
		assertTrue(new File(NAMESPACE_COUPLING_INFO).exists());
		assertEquals(csv.generateNamespaceCoupling(), readStringFrom(NAMESPACE_COUPLING_INFO));
	}

	@Test
	public void testForInternalDependenciesFile() {
		assertTrue(file.generateInternalDependenciesFile(INTERNAL_DEPENDENCIES_INFO));
		assertTrue(new File(INTERNAL_DEPENDENCIES_INFO).exists());
		assertEquals(json.generateInternalDependencies(), readStringFrom(INTERNAL_DEPENDENCIES_INFO));
	}

	@Test
	public void testForCyclicDependenciesFile() {
		assertTrue(file.generateCyclicDependenciesFile(CYCLIC_DEPENDENCIES_INFO));
		assertTrue(new File(CYCLIC_DEPENDENCIES_INFO).exists());
		assertEquals(csv.generateCyclicDependencies(), readStringFrom(CYCLIC_DEPENDENCIES_INFO));
	}

	@Test
	public void testForMetricThresholdsFile() {
		assertTrue(file.generateMetricThresholdsFile(METRIC_THRESHOLDS_INFO));
		assertTrue(new File(METRIC_THRESHOLDS_INFO).exists());
		assertEquals(csv.generateThresholds(), readStringFrom(METRIC_THRESHOLDS_INFO));
	}

	@Test
	public void testForTypeCouplingFile() {
		assertTrue(file.generateTypeCouplingFile(TYPE_COUPLING_INFO));
		assertTrue(new File(TYPE_COUPLING_INFO).exists());
		assertEquals(csv.generateTypeCoupling(), readStringFrom(TYPE_COUPLING_INFO));
	}
	
	@Test
	public void testForTypeResonanceFile() {
		assertTrue(file.generateTypesResonanceFile(TYPE_RESONANCE_INFO));
		assertTrue(new File(TYPE_RESONANCE_INFO).exists());
		assertEquals(json.generateTypesResonance(), readStringFrom(TYPE_RESONANCE_INFO));
	}

	private String readStringFrom(String fileName) {
		String content = "";
		try {
			content = FileUtils.readFileToString(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
