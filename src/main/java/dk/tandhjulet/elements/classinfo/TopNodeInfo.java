package dk.tandhjulet.elements.classinfo;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import javax.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import dk.tandhjulet.elements.utils.TopNode;

public class TopNodeInfo {

	static {
		Classes.registerClass(new ClassInfo<>(TopNode.class, "topnode")
			.user("topnode")
			.name("node")
			.description("Represents a node of a sorted list.")
			.parser(new Parser<TopNode>() {
				
				@Override
				@Nullable
				public TopNode parse(String input, ParseContext context) {
					return null;
				}

				@Override
				public boolean canParse(ParseContext contect) {
					return false;
				}

				@Override
				public String toVariableNameString(TopNode node) {
					return "#" + (node.getLocation()+1) + " " + node.getKey() + ": " + node.getValue();
				}

				@Override
				public String getVariableNamePattern() {
					return "#\\d* .*?: \\d*";
				}

				@Override
				public String toString(TopNode node, int flags) {
					return toVariableNameString(node);
				}
			})
			.serializer(new Serializer<TopNode>() {

				@Override
				public Fields serialize(TopNode node) throws NotSerializableException {
					Fields fields = new Fields();
					fields.putPrimitive("key", node.getKey());
					fields.putPrimitive("value", node.getValue());
					fields.putPrimitive("location", node.getLocation());
					return fields;
				}


				@Override
				public TopNode deserialize(Fields fields) throws StreamCorruptedException {
					return new TopNode((String) fields.getPrimitive("key"), (Long) fields.getPrimitive("value"), (int) fields.getPrimitive("location"));
				}

				@Override
				public void deserialize(TopNode node, Fields fields) throws StreamCorruptedException {
					assert false;
				}

				@Override
				public boolean mustSyncDeserialization() {
					return true;
				}
				
				@Override
				protected boolean canBeInstantiated() {
					return false;
				}
			})
		);
	}

}