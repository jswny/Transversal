package filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import sorting.MergeSorter;
import structures.Queue;

/**
 * An iterator to perform a level order traversal of part of a 
 * filesystem. A level-order traversal is equivalent to a breadth-
 * first search.
 * 
 * @author liberato
 *
 */
public class LevelOrderIterator extends FileIterator<File> {	
	/**
	 * Instantiate a new LevelOrderIterator, rooted at the rootNode.
	 * @param rootNode
	 * @throws FileNotFoundException if the rootNode does not exist
	 */
	
	Queue<File> result = new Queue<File>(); 
	Queue<File> tempDirQ = new Queue<File>(); 
	Queue<File> tempFileQ = new Queue<File>();
	
	MergeSorter<File> sort = new MergeSorter<File>();
	
	public LevelOrderIterator(File rootNode) throws FileNotFoundException {
		
		if (!rootNode.exists()) {
			throw new FileNotFoundException();
		}
		
		result.enqueue(rootNode);
		
		fileHelper(rootNode);
	}
	
	public void fileHelper(File root) {
		if (root.isFile()) {
			return;
		}
		
		File[] fileArr = root.listFiles();
		
		if (fileArr.length == 0) {
			return;
		}
		
		for (File currNode : fileArr) {
			if(currNode.isDirectory()) {
				tempDirQ.enqueue(currNode);
			}
			
			tempFileQ.enqueue(currNode);
		}
		
		sort.mergeSort(tempDirQ);
		sort.mergeSort(tempFileQ);
		
		while (!tempFileQ.isEmpty()) {
			result.enqueue(tempFileQ.dequeue());
		}
		
		while (!tempDirQ.isEmpty()) {
			fileHelper(tempDirQ.dequeue());
		}
	}
	
	@Override
	public boolean hasNext() {
		if (result.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public File next() throws NoSuchElementException {
		if (result.isEmpty()) throw new NoSuchElementException();
		
		return result.dequeue();
	}

	@Override
	public void remove() {
		// Leave this one alone.
		throw new UnsupportedOperationException();		
	}

}
