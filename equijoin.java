import java.io.IOException;
import java.util.*;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;


public class equijoin {

	public static String tableNo1 = "";
	public static String tableNo2 = "";
	

	public static class Map extends Mapper<Text, Text, Text, Text> {

		private Text keyJoinCol = new Text();
		private Text valEquiJoin = new Text();

		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			String row = key.toString();
			String[] columns = key.toString().split(",");

			if (row != null) {
				
				if (tableNo1.isEmpty())
					tableNo1 = columns[0];
				
				else {
					
					if (!tableNo1.equals(columns[0])) {
						tableNo2 = columns[0];
					}
				}
			}

			keyJoinCol.set(columns[1]);
			valEquiJoin.set(row);
			context.write(keyJoinCol, valEquiJoin);

		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			HashSet<String> valuesLeft = new HashSet<String>();
			HashSet<String> valuesRight = new HashSet<String>();

			for (Text txt : values) {
				
				String[] str = txt.toString().split(",");
				
				if (str != null && str[0].trim().equals(tableNo1)) {
					valuesLeft.add(txt.toString());
					
				} else if (str != null && str[0].trim().equals(tableNo2)) {
					valuesRight.add(txt.toString());
				}

			}

			
			for (String left: valuesLeft) {

				for (String right: valuesRight) {

					String finalRow = left + ", " + right;
					Text finalval= new Text(finalRow);
					context.write(null, finalval);
				}
			}

		}
	}

	public static void main(String[] args) throws Exception {
		
		Configuration config = new Configuration();

		@SuppressWarnings("deprecation")
		Job equiJoinjob = new Job(config, "equiJoin");
		
		equiJoinjob.setMapperClass(Map.class);
		equiJoinjob.setReducerClass(Reduce.class);

		equiJoinjob.setOutputKeyClass(Text.class);
		equiJoinjob.setOutputValueClass(Text.class);

		equiJoinjob.setMapOutputKeyClass(Text.class);
		equiJoinjob.setMapOutputValueClass(Text.class);

		equiJoinjob.setInputFormatClass(KeyValueTextInputFormat.class);
		equiJoinjob.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(equiJoinjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(equiJoinjob, new Path(args[1]));

		equiJoinjob.waitForCompletion(true);
	}
	
	

}
