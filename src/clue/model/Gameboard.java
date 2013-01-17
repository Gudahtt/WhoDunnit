package clue.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import clue.api.model.Direction;
import clue.model.Card.Type;

public class Gameboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6627041240528084061L;
	private HashMap<Position, RoomNode> roomNodes;
	private HashMap<Card, RoomNode> nameMap;
	private ArrayList<HallwayNode> hallwayNodes;
	int[][] paths;

	public Gameboard() {
		roomNodes = new HashMap<Position, RoomNode>();
		nameMap = new HashMap<Card, RoomNode>();
		hallwayNodes = new ArrayList<HallwayNode>();

		// initialize rooms
		RoomNode hall = new RoomNode(Card.HALL);
		RoomNode lounge = new RoomNode(Card.LOUNGE);
		RoomNode diningRoom = new RoomNode(Card.DINING_ROOM);
		RoomNode kitchen = new RoomNode(Card.KITCHEN);
		RoomNode ballRoom = new RoomNode(Card.BALL_ROOM);
		RoomNode conservatory = new RoomNode(Card.CONSERVATORY);
		RoomNode billiardRoom = new RoomNode(Card.BILLIARD_ROOM);
		RoomNode library = new RoomNode(Card.LIBRARY);
		RoomNode study = new RoomNode(Card.STUDY);

		// populating nameMap
		nameMap.put(Card.HALL, hall);
		nameMap.put(Card.LOUNGE, lounge);
		nameMap.put(Card.DINING_ROOM, diningRoom);
		nameMap.put(Card.KITCHEN, kitchen);
		nameMap.put(Card.BALL_ROOM, ballRoom);
		nameMap.put(Card.CONSERVATORY, conservatory);
		nameMap.put(Card.BILLIARD_ROOM, billiardRoom);
		nameMap.put(Card.LIBRARY, library);
		nameMap.put(Card.STUDY, study);

		// row 0
		int j = 0;
		hallwayNodes.add(new HallwayNode(new Position(9, j)));
		hallwayNodes.add(new HallwayNode(new Position(14, j)));

		j = 1;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 5) {
				roomNodes.put(new Position(i, j), kitchen);
			} else if (i >= 10 && i <= 13) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), conservatory);
			} else if ((i >= 7 && i <= 9) || (i >= 14 && i <= 16)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 2;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 5) {
				roomNodes.put(new Position(i, j), kitchen);
			} else if (i >= 8 && i <= 15) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), conservatory);
			} else if ((i >= 6 && i <= 7) || (i >= 16 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 3;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 5) {
				roomNodes.put(new Position(i, j), kitchen);
			} else if (i >= 8 && i <= 15) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), conservatory);
			} else if ((i >= 6 && i <= 7) || (i >= 16 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 4;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 5) {
				roomNodes.put(new Position(i, j), kitchen);
			} else if (i >= 8 && i <= 15) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), conservatory);
			} else if ((i >= 6 && i <= 7) || (i >= 16 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 5;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 5) {
				roomNodes.put(new Position(i, j), kitchen);
			} else if (i >= 8 && i <= 15) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if (i >= 19 && i <= 22) {
				roomNodes.put(new Position(i, j), conservatory);
			} else if ((i >= 6 && i <= 7) || (i >= 16 && i <= 18)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 6;
		for (int i = 0; i < 25; i++) {
			if (i >= 1 && i <= 5) {
				roomNodes.put(new Position(i, j), kitchen);
			} else if (i >= 8 && i <= 15) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if ((i >= 6 && i <= 7) || (i >= 16 && i <= 23)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 7;
		for (int i = 0; i < 25; i++) {
			if (i >= 8 && i <= 15) {
				roomNodes.put(new Position(i, j), ballRoom);
			} else if ((i >= 0 && i <= 7) || (i >= 16 && i <= 22)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 8;
		for (int i = 0; i < 25; i++) {
			if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), billiardRoom);
			} else if (i >= 1 && i <= 17) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 9;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 4) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), billiardRoom);
			} else if (i >= 5 && i <= 17) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 10;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 7) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), billiardRoom);
			} else if ((i >= 8 && i <= 9) || (i >= 15 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 11;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 7) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), billiardRoom);
			} else if ((i >= 8 && i <= 9) || (i >= 15 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 12;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 7) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), billiardRoom);
			} else if ((i >= 8 && i <= 9) || (i >= 15 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 13;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 7) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if ((i >= 8 && i <= 9) || (i >= 15 && i <= 22)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 14;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 7) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if (i >= 18 && i <= 22) {
				roomNodes.put(new Position(i, j), library);
			} else if ((i >= 8 && i <= 9) || (i >= 15 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 15;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 7) {
				roomNodes.put(new Position(i, j), diningRoom);
			} else if (i >= 17 && i <= 23) {
				roomNodes.put(new Position(i, j), library);
			} else if ((i >= 8 && i <= 9) || (i >= 15 && i <= 16)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 16;
		for (int i = 0; i < 25; i++) {
			if (i >= 17 && i <= 23) {
				roomNodes.put(new Position(i, j), library);
			} else if ((i >= 1 && i <= 9) || (i >= 15 && i <= 16)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 17;
		for (int i = 0; i < 25; i++) {
			if (i >= 17 && i <= 23) {
				roomNodes.put(new Position(i, j), library);
			} else if (i >= 0 && i <= 16) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 18;
		for (int i = 0; i < 25; i++) {
			if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if (i >= 18 && i <= 22) {
				roomNodes.put(new Position(i, j), library);
			} else if ((i >= 1 && i <= 8) || (i >= 15 && i <= 17)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 19;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 6) {
				roomNodes.put(new Position(i, j), lounge);
			} else if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if ((i >= 7 && i <= 8) || (i >= 15 && i <= 23)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 20;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 6) {
				roomNodes.put(new Position(i, j), lounge);
			} else if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if ((i >= 7 && i <= 8) || (i >= 15 && i <= 22)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 21;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 6) {
				roomNodes.put(new Position(i, j), lounge);
			} else if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if (i >= 17 && i <= 23) {
				roomNodes.put(new Position(i, j), study);
			} else if ((i >= 7 && i <= 8) || (i >= 15 && i <= 16)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 22;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 6) {
				roomNodes.put(new Position(i, j), lounge);
			} else if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if (i >= 17 && i <= 23) {
				roomNodes.put(new Position(i, j), study);
			} else if ((i >= 7 && i <= 8) || (i >= 15 && i <= 16)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 23;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 6) {
				roomNodes.put(new Position(i, j), lounge);
			} else if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if (i >= 17 && i <= 23) {
				roomNodes.put(new Position(i, j), study);
			} else if ((i >= 7 && i <= 8) || (i >= 15 && i <= 16)) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		j = 24;
		for (int i = 0; i < 25; i++) {
			if (i >= 0 && i <= 5) {
				roomNodes.put(new Position(i, j), lounge);
			} else if (i >= 9 && i <= 14) {
				roomNodes.put(new Position(i, j), hall);
			} else if (i >= 18 && i <= 23) {
				roomNodes.put(new Position(i, j), study);
			} else if (i == 7 || i == 16) {
				hallwayNodes.add(new HallwayNode(new Position(i, j)));
			}
		}

		this.connectGNodes();
	}

	private void connectGNodes() {
		// connecting the hallway nodes
		for (HallwayNode a : hallwayNodes) {
			Position aPos = a.getLocation();

			Position above = new Position(aPos.getX(), aPos.getY() - 1);
			Position below = new Position(aPos.getX(), aPos.getY() + 1);
			Position left = new Position(aPos.getX() - 1, aPos.getY());
			Position right = new Position(aPos.getX() + 1, aPos.getY());

			for (HallwayNode b : hallwayNodes) {
				if (b.getLocation().equals(above)) {
					a.setNorth(b);
				}
				if (b.getLocation().equals(below)) {
					a.setSouth(b);
				}
				if (b.getLocation().equals(left)) {
					a.setWest(b);
				}
				if (b.getLocation().equals(right)) {
					a.setEast(b);
				}
			}
		}

		// Connecting the entrances
		ArrayList<GNode> rooms = this.getRooms();

		GNode hall = rooms.get(0);
		GNode lounge = rooms.get(1);
		GNode diningRoom = rooms.get(2);
		GNode kitchen = rooms.get(3);
		GNode ballRoom = rooms.get(4);
		GNode conservatory = rooms.get(5);
		GNode billiardRoom = rooms.get(6);
		GNode library = rooms.get(7);
		GNode study = rooms.get(8);

		// hall
		Position p = new Position(12, 17);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				hall.setNorth(n);
				n.setSouth(hall);
			}
		}
		p = new Position(11, 17);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				hall.setWest(n);
				n.setSouth(hall);
			}
		}
		p = new Position(15, 20);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				hall.setEast(n);
				n.setWest(hall);
			}
		}

		// lounge
		p = new Position(6, 18);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				lounge.setNorth(n);
				n.setSouth(lounge);
			}
		}

		// diningRoom
		p = new Position(6, 16);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				diningRoom.setSouth(n);
				n.setNorth(diningRoom);
			}
		}
		p = new Position(8, 12);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				diningRoom.setEast(n);
				n.setWest(diningRoom);
			}
		}

		// kitchen
		p = new Position(4, 7);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				kitchen.setSouth(n);
				n.setNorth(kitchen);
			}
		}

		// ballRoom
		p = new Position(7, 5);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				ballRoom.setWest(n);
				n.setEast(ballRoom);
			}
		}
		p = new Position(9, 8);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				ballRoom.setNorth(n);
				n.setNorth(ballRoom);
			}
		}
		p = new Position(14, 8);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				ballRoom.setSouth(n);
				n.setNorth(ballRoom);
			}
		}
		p = new Position(16, 5);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				ballRoom.setEast(n);
				n.setWest(ballRoom);
			}
		}

		// conservatory
		p = new Position(18, 5);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				conservatory.setSouth(n);
				n.setNorth(conservatory);
			}
		}

		// billiardRoom
		p = new Position(17, 9);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				billiardRoom.setWest(n);
				n.setEast(billiardRoom);
			}
		}
		p = new Position(22, 13);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				billiardRoom.setSouth(n);
				n.setNorth(billiardRoom);
			}
		}

		// library
		p = new Position(20, 13);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				library.setNorth(n);
				n.setSouth(library);
			}
		}
		p = new Position(16, 16);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				library.setWest(n);
				n.setEast(library);
			}
		}

		// study
		p = new Position(17, 20);
		for (HallwayNode n : hallwayNodes) {
			if (p.equals(n.getLocation())) {
				study.setNorth(n);
				n.setSouth(study);
			}
		}

		// connecting the secret entrances
		lounge.setSouth(conservatory);
		kitchen.setNorth(study);
		conservatory.setEast(lounge);
		study.setEast(kitchen);

	}

	public GNode getGNode(Position p) {
		if (roomNodes.containsKey(p))
			return roomNodes.get(p);
		else {
			for (HallwayNode n : hallwayNodes) {
				if (n.getLocation().equals(p)) {
					return n;
				}
			}
			return null;
		}
	}

	public Position getGNodePosition(GNode n) {
		if (n.isRoom()) {
			return this.getRoomPosition(this.getRoomCard((RoomNode) n));
		} else {
			return ((HallwayNode) n).getLocation();
		}
	}

	public ArrayList<GNode> getRooms() {
		ArrayList<GNode> rooms = new ArrayList<GNode>();

		rooms.add(roomNodes.get(new Position(10, 21)));
		rooms.add(roomNodes.get(new Position(3, 21)));
		rooms.add(roomNodes.get(new Position(4, 12)));
		rooms.add(roomNodes.get(new Position(2, 3)));
		rooms.add(roomNodes.get(new Position(11, 4)));
		rooms.add(roomNodes.get(new Position(20, 3)));
		rooms.add(roomNodes.get(new Position(20, 10)));
		rooms.add(roomNodes.get(new Position(20, 16)));
		rooms.add(roomNodes.get(new Position(20, 23)));

		return rooms;
	}

	public Card getRoomCard(RoomNode n) {
		ArrayList<Card> rooms = Card.getTypes(Type.ROOM);

		if (n.equals(roomNodes.get(new Position(10, 21)))) {
			return rooms.get(0);
		} else if (n.equals(roomNodes.get(new Position(3, 21)))) {
			return rooms.get(1);
		} else if (n.equals(roomNodes.get(new Position(4, 12)))) {
			return rooms.get(2);
		} else if (n.equals(roomNodes.get(new Position(2, 3)))) {
			return rooms.get(3);
		} else if (n.equals(roomNodes.get(new Position(11, 4)))) {
			return rooms.get(4);
		} else if (n.equals(roomNodes.get(new Position(20, 3)))) {
			return rooms.get(5);
		} else if (n.equals(roomNodes.get(new Position(20, 10)))) {
			return rooms.get(6);
		} else if (n.equals(roomNodes.get(new Position(20, 16)))) {
			return rooms.get(7);
		} else if (n.equals(roomNodes.get(new Position(20, 23)))) {
			return rooms.get(8);
		} else
			return null;
	}

	public ArrayList<GNode> getTiles() {
		ArrayList<GNode> tiles = new ArrayList<GNode>();
		for (HallwayNode n : this.hallwayNodes) {
			tiles.add(n);
		}
		return tiles;
	}

	@Override
	public String toString() {
		return "Rooms: " + roomNodes.toString() + ", Hallway tiles: "
				+ hallwayNodes.toString();
	}

	// returns array of every single room position for a given roomNode
	public ArrayList<Position> getRoomPositions(GNode n) {
		ArrayList<Position> roomPositions = new ArrayList<Position>();

		for (Entry<Position, RoomNode> entry : this.roomNodes.entrySet()) {
			if (entry.getValue() == n)
				roomPositions.add(entry.getKey());
		}

		return roomPositions;
	}

	public HashMap<Card, RoomNode> getNameMap() {
		return nameMap;
	}

	// returns the position of the "middle" of the room, i.e. first token
	// position
	public Position getRoomPosition(Card room) {
		switch (room) {
		case HALL:
			return new Position(12, 21);
		case LOUNGE:
			return new Position(4, 21);
		case DINING_ROOM:
			return new Position(5, 12);
		case KITCHEN:
			return new Position(4, 3);
		case BALL_ROOM:
			return new Position(13, 4);
		case CONSERVATORY:
			return new Position(20, 3);
		case BILLIARD_ROOM:
			return new Position(21, 10);
		case LIBRARY:
			return new Position(21, 16);
		case STUDY:
			return new Position(20, 23);
		default:
			return null;
		}
	}

	public GNode getRoomNode(Card room) {
		Position roomPosition = getRoomPosition(room);

		return this.getGNode(roomPosition);
	}

	public HashMap<Position, Direction> getEntrancePositions() {
		HashMap<Position, Direction> entrances = new HashMap<Position, Direction>();

		for (GNode a : this.getRooms()) {

			for (GNode node : a.getEdges()) {
				if (!node.isRoom()) {
					HallwayNode outsideDoor = (HallwayNode) node;
					Position outsideDoorPos = outsideDoor.getLocation();
					Direction direction; // direction doorway is facing
					Position entrancePosition;

					if (outsideDoor.getNorth() == a) {
						direction = Direction.SOUTH;
						entrancePosition = new Position(outsideDoorPos.getX(),
								outsideDoorPos.getY() - 1);
					} else if (outsideDoor.getEast() == a) {
						direction = Direction.WEST;
						entrancePosition = new Position(
								outsideDoorPos.getX() + 1,
								outsideDoorPos.getY());
					} else if (outsideDoor.getSouth() == a) {
						direction = Direction.NORTH;
						entrancePosition = new Position(outsideDoorPos.getX(),
								outsideDoorPos.getY() + 1);
					} else {
						direction = Direction.EAST;
						entrancePosition = new Position(
								outsideDoorPos.getX() - 1,
								outsideDoorPos.getY());
					}

					entrances.put(entrancePosition, direction);
				}
			}
		}

		return entrances;
	}

	// returns array of 6 token positions for the given room
	public ArrayList<Position> getRoomTokenPositions(GNode n) {
		ArrayList<GNode> rooms = this.getRooms();

		if (n == rooms.get(0)) {
			ArrayList<Position> hall = new ArrayList<Position>();
			hall.add(new Position(12, 21));
			hall.add(new Position(11, 21));
			hall.add(new Position(12, 22));
			hall.add(new Position(11, 22));
			hall.add(new Position(10, 21));
			hall.add(new Position(10, 22));
			hall.add(new Position(13, 21));
			return hall;
		} else if (n == rooms.get(1)) {
			ArrayList<Position> lounge = new ArrayList<Position>();
			lounge.add(new Position(4, 21));
			lounge.add(new Position(3, 21));
			lounge.add(new Position(4, 22));
			lounge.add(new Position(3, 22));
			lounge.add(new Position(2, 21));
			lounge.add(new Position(2, 22));
			lounge.add(new Position(5, 21));
			return lounge;
		} else if (n == rooms.get(2)) {
			ArrayList<Position> diningRoom = new ArrayList<Position>();
			diningRoom.add(new Position(5, 12));
			diningRoom.add(new Position(4, 12));
			diningRoom.add(new Position(5, 13));
			diningRoom.add(new Position(4, 12));
			diningRoom.add(new Position(3, 12));
			diningRoom.add(new Position(3, 13));
			diningRoom.add(new Position(2, 12));
			return diningRoom;
		} else if (n == rooms.get(3)) {
			ArrayList<Position> kitchen = new ArrayList<Position>();
			kitchen.add(new Position(4, 3));
			kitchen.add(new Position(3, 3));
			kitchen.add(new Position(4, 4));
			kitchen.add(new Position(3, 4));
			kitchen.add(new Position(2, 3));
			kitchen.add(new Position(2, 4));
			kitchen.add(new Position(1, 3));
			return kitchen;
		} else if (n == rooms.get(4)) {
			ArrayList<Position> ballRoom = new ArrayList<Position>();
			ballRoom.add(new Position(13, 4));
			ballRoom.add(new Position(12, 4));
			ballRoom.add(new Position(13, 5));
			ballRoom.add(new Position(12, 5));
			ballRoom.add(new Position(11, 4));
			ballRoom.add(new Position(11, 5));
			ballRoom.add(new Position(10, 4));
			return ballRoom;
		} else if (n == rooms.get(5)) {
			ArrayList<Position> conservatory = new ArrayList<Position>();
			conservatory.add(new Position(20, 3));
			conservatory.add(new Position(21, 3));
			conservatory.add(new Position(20, 4));
			conservatory.add(new Position(21, 4));
			conservatory.add(new Position(19, 3));
			conservatory.add(new Position(19, 4));
			conservatory.add(new Position(22, 3));
			return conservatory;
		} else if (n == rooms.get(6)) {
			ArrayList<Position> billiardRoom = new ArrayList<Position>();
			billiardRoom.add(new Position(21, 10));
			billiardRoom.add(new Position(20, 10));
			billiardRoom.add(new Position(21, 11));
			billiardRoom.add(new Position(20, 11));
			billiardRoom.add(new Position(22, 10));
			billiardRoom.add(new Position(22, 11));
			billiardRoom.add(new Position(19, 10));
			return billiardRoom;
		} else if (n == rooms.get(7)) {
			ArrayList<Position> library = new ArrayList<Position>();
			library.add(new Position(21, 16));
			library.add(new Position(20, 16));
			library.add(new Position(21, 17));
			library.add(new Position(20, 17));
			library.add(new Position(19, 16));
			library.add(new Position(19, 17));
			library.add(new Position(22, 16));
			return library;
		} else if (n == rooms.get(8)) {
			ArrayList<Position> study = new ArrayList<Position>();
			study.add(new Position(20, 23));
			study.add(new Position(19, 23));
			study.add(new Position(20, 24));
			study.add(new Position(19, 24));
			study.add(new Position(21, 23));
			study.add(new Position(21, 24));
			study.add(new Position(18, 23));
			return study;
		} else
			return null;
	}

	public ArrayList<Position> getRoomTokenPositions(Card room) {
		ArrayList<GNode> rooms = this.getRooms();

		switch (room) {
		case HALL:
			return getRoomTokenPositions(rooms.get(0));
		case LOUNGE:
			return getRoomTokenPositions(rooms.get(1));
		case DINING_ROOM:
			return getRoomTokenPositions(rooms.get(2));
		case KITCHEN:
			return getRoomTokenPositions(rooms.get(3));
		case BALL_ROOM:
			return getRoomTokenPositions(rooms.get(4));
		case CONSERVATORY:
			return getRoomTokenPositions(rooms.get(5));
		case BILLIARD_ROOM:
			return getRoomTokenPositions(rooms.get(6));
		case LIBRARY:
			return getRoomTokenPositions(rooms.get(7));
		case STUDY:
			return getRoomTokenPositions(rooms.get(8));
		default:
			return null;
		}
	}

}
