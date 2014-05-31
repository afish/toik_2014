package pl.agh.iet.i.toik.cloudsync.logic.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PersistenceServiceImplTest {
	private PersistenceService service;
    private File testDbFile;
    private Random rand = new Random();

	@Before
	public void setUp() {
        try {
            testDbFile = File.createTempFile("test_database_", ".db");
            service = new PersistenceServiceImpl(testDbFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    @After
    public void tearDown() {
        testDbFile.delete();
    }

	@Test
	public void testGetNextFromSequence() {
        int first = service.getNextFromSequence("test", "test");

		assertEquals(0, first);
	}

    @Test
    public void testGetNextTwiceFromSequence() {
        int first = service.getNextFromSequence("test", "test");
        int second = service.getNextFromSequence("test", "test");

        assertEquals(0, first);
        assertEquals(1, second);
    }

    @Test
    public void testGetNextFromTwoDifferentSequencesWithTheSamePrefix() {
        int first = service.getNextFromSequence("test", "test");
        int second = service.getNextFromSequence("test", "test_second");

        assertEquals(0, first);
        assertEquals(0, second);
    }

    @Test
    public void testGetNonExistingElement() {
        String nonExisting = service.get("test", "test");
        assertEquals(null, nonExisting);
    }

    @Test
    public void testGetNextFromTwoDifferentSequencesWithDifferentPrefix() {
        int first = service.getNextFromSequence("test", "test");
        int second = service.getNextFromSequence("test_second", "test");

        assertEquals(0, first);
        assertEquals(0, second);
    }

    @Test
    public void testPutGetNewElement() {
        String object = "test_object";

        service.put("test", "test", object);
        String getResult = service.get("test", "test");

        assertEquals(object, getResult);
    }

    @Test
    public void testPutChangeGetElement() {
        String object = "test_object";
        String changedObject = "test_object_2";

        service.put("test", "test", object);
        service.put("test", "test", changedObject);
        String getResult = service.get("test", "test");

        assertEquals(changedObject, getResult);
    }

    @Test
    public void testPutTwoElementsWithTheSamePrefix() {
        String object = "test_object";
        String secondObject = "test_object_2";

        service.put("test", "test", object);
        service.put("test", "test2", secondObject);
        String getResult = service.get("test", "test");
        String secondGetResult = service.get("test", "test2");

        assertEquals(object, getResult);
        assertEquals(secondObject, secondGetResult);
    }

    @Test
    public void testPutGetNewElementsUnderDifferentPrefixes() {
        String object = "test_object";
        String secondObject = "test_object_2";

        service.put("test", "test", object);
        service.put("test2", "test", secondObject);
        String getResult = service.get("test", "test");
        String secondGetResult = service.get("test2", "test");

        assertEquals(object, getResult);
        assertEquals(secondObject, secondGetResult);
    }

    @Test
    public void testPutGetRemoveElement() {
        String object = "test_object";

        service.put("test", "test", object);
        service.remove("test", "test");
        String getResult = service.get("test", "test");

        assertEquals(null, getResult);
    }

    @Test
    public void testHasNonExistingElement() {
        boolean result = service.has("test", "test");
        assertEquals(false, result);
    }

    @Test
    public void testPutHasElement() {
        String object = "test_object";

        service.put("test", "test", object);
        boolean result = service.has("test", "test");

        assertEquals(true, result);
    }
}
