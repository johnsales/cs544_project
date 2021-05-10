package cs544.team7.project.model;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public enum RoleType {
	CLIENT("Client"), ADMIN("Admin"), PROVIDER("Provider");
	private String type;

	private RoleType(String type) {
		this.type = type;
	}
}