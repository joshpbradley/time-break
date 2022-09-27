public class Confusion extends StatusEffect
{
    private boolean[] stolen;
    private boolean[] injected;
    private int[] mapping = new int[4];

    public Confusion()
    {
        super(2000);
    }

    public void apply(AbstractPlayer afflicted)
    {
        if(stolen == null)
        {
            stolen = afflicted.getController().getKeyboard();
            injected = new boolean[stolen.length];
            System.arraycopy(stolen, 0, injected, 0, stolen.length);

            //Mapping Phase
            for (int i = 0; i < mapping.length; i++)
            {
                mapping[i] = -1;

                int key = (int) (Math.random() * 5 - 1);
                if(key == i)
                {
                    key++;
                }

                for (int j = i; j > -1; j--)
                {
                    if (mapping[j] == key)
                    {
                        key++;
                        if(key > mapping.length - 1)
                        {
                            key = 0;
                        }
                        //Reset loop
                        j = i + 1;
                    }
                }
                mapping[i] = key;
                injected[i] = stolen[key];
            }
            //Injecting
            afflicted.getController().setKeyboard(injected);
        }
        else
        {
            boolean continueEffect = false;
            for (int i = 0; i < mapping.length; i++)
            {
                if (stolen[mapping[i]])
                {
                    continueEffect = true;
                }
                injected[i] = stolen[mapping[i]];
            }
            if(!continueEffect)
                forceEnd(afflicted);
        }
    }

    protected void endEffect(AbstractPlayer afflicted)
    {
        afflicted.getController().setKeyboard(stolen);
    }
}
