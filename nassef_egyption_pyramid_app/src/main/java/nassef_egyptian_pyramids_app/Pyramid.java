package nassef_egyptian_pyramids_app;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected String[] contributors;

  // constructor
  public Pyramid(
      Integer pyramidId,
      String pyramidName,
      String[] pyramidContributors) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
  }

  // print contributors
  public void printContributors(Pharaoh[] tempPharaoh) {
    int counter = 1;
    Integer total = 0;
    String name = "";
    Integer cont = 0;
    for (int i = 0; i < contributors.length; i++) {
      for (int j = 0; j < tempPharaoh.length; j++) {
        String temPharaoh = tempPharaoh[j].hieroglyphic;
        if (temPharaoh.equals(contributors[i])) {
          name = tempPharaoh[j].name;
          cont = tempPharaoh[j].contribution;
          total += tempPharaoh[j].contribution;
          break;
        }
      }
      System.out.println("        Contributor " + counter + ": " + name + " " + cont + " gold coins");
      counter++;
    }
    System.out.println("        Total contribution: " + total + " gold coins");
  }

  // print pyramid
  public void print(Pharaoh[] tempPharaoh) {
    System.out.printf("Pyramid %s\n", name);
    System.out.printf("\tid: %d\n", id);
    printContributors(tempPharaoh);
  }

  // prints only the name of a pyramid
  public void printName(Pharaoh[] tempPharaoh) {
    System.out.println("    " + name);
  }

}
