import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NameFetcher {

	public static void main(String[] args) {
		System.out.println("Enter the email id you want to look up:");

		// Create BufferedReader to read console input from user
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Get the email id that the user had entered
			String emailId = input.readLine();
			input.close();

			// Create the web address and create a URL object with it that we can use later
			String webAddress = "http://www.ecs.soton.ac.uk/people/";
			webAddress = webAddress.concat(emailId);
			URL webUrl = new URL(webAddress);

			// Create the BufferedReader to download the web page
			BufferedReader downloader = new BufferedReader(new InputStreamReader(webUrl.openStream()));
			String inputLine;
			Boolean found = false;

			// Go through each line of the web page
			while((inputLine = downloader.readLine()) != null) {
				if(inputLine.contains("property=\"name\"")) {
					found = true;

					// If this line contains the name then get the start and end positions on this line
					Integer start = inputLine.indexOf("property=\"name\">");
					Integer end = inputLine.indexOf("<", start);

					// Trim the line
					String name = inputLine.substring(start, end);
					Integer tag = name.indexOf(">");

					// Remove the tag at the start of the name
					name = name.substring(tag + 1);

					// Print out the name and break because we don't need to read any more of the web page
					System.out.println(name);
					break;
				}
			}

			// There was some error that prevented us getting the name
			if(!found) {
				System.out.println("Name could not be found");
			}

			// Clean up and close the reader
			downloader.close();
		}
		catch(Exception e) {
			// If we get an error then output it for further diagnostics
			e.printStackTrace();
		}
	}
}
