package structures;

import java.util.NoSuchElementException;

public class Queue<T> implements UnboundedQueueInterface<T> {
	
	Node<T> head = null;
	Node<T> tail = null;
	int size = 0;
	
	public Queue() {
	}
	
	public Queue(Queue<T> other) {
		if (other.head == null) {
			this.head = other.head;
		} else {
			this.head = new Node<T>(other.head.getData(), other.head.getNext());
		}
		if (other.tail == null) {
			this.tail = other.tail;
		} else { 
			this.tail = new Node<T>(other.tail.getData(), other.tail.getNext());
		}
		this.size = other.size;
	}
	
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void enqueue(T element) {
		if (isEmpty()) {
			head = new Node<T>(element, null);
			tail = head;
		} else {
			Node<T> newNode = new Node<T>(element, null);
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
	}

	@Override
	public T dequeue() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		T data = head.getData();
		head = head.getNext();
		size--;
		return data;
	}

	@Override
	public T peek() throws NoSuchElementException {
		return head.getData();
	}

	@Override
	public UnboundedQueueInterface<T> reversed() {
		Queue<T> Q = new Queue<T>();
		LinkedStack<T> stack = new LinkedStack<T>();
		while (!this.isEmpty()) {
			stack.push(this.dequeue());
		}
		while (!stack.isEmpty()) {
			Q.enqueue(stack.pop());
		}
		return Q;
	}
}
