import java.io.File;
import java.util.Random;

import org.hackrslab.avatar.RandomAvatar;
import org.hackrslab.avatar.RandomAvatarBuilder;



public class Main
{
    
    
    public static void main(final String[] args) {
    
    
        new Random();
        final RandomAvatar generator =
                new RandomAvatarBuilder().squareSize(400).blockSize(5).asymmetry(false).cache(true)
                        // since 0.2.3
                        .padding(20).backgroundColor(0xeeeeee)
                        // since 0.2.1
                        .fontColor(0xffffff)
                        // since 0.2.1
                        .addColor(127, 127, 220).addColor(100, 207, 172).addColor(198, 87, 181)
                        .addColor(134, 166, 220).build();
        
        final String[] initials = "sylvain.leroy@tocea.com".split(",");
        for (int i = 0; i < initials.length; i++) {
            generator.generate(new File("avatar" + i + "-default.png"));
            // since 0.2.1
            generator.generate(new File("avatar" + i + "-initial-1.png"),
                    RandomAvatar.Extra.initial(initials[i]));
            generator.generate(new File("avatar" + i + "-initial-3.png"),
                    RandomAvatar.Extra.initial(initials[i], 3));
            generator.generate(new File("avatar" + i + "-seed-1.png"),
                    RandomAvatar.Extra.seed(initials[i]));
            generator.generate(new File("avatar" + i + "-seed-2.png"),
                    RandomAvatar.Extra.seed(initials[i], 3));
        }
        // OR generator.generate(new XyzOutputStream(), ...);
    }
}
