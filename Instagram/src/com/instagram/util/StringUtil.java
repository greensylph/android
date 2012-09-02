package com.instagram.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
  private static Pattern emojiRegexp;
  private static Pattern hashRegexp;
  private static Pattern mentionRegexp = Pattern.compile("(^|[^a-zA-Z0-9_]+)(@([a-zA-Z0-9_]+))", 2);

  static
  {
    hashRegexp = Pattern.compile("(#\\w+)", 2);
    emojiRegexp = Pattern.compile("[🀄🅰🅱🅾🅿🆎🆒🆔🆕🆗🆙🆚🇧🇨🇩🇪🇪🇫🇬🇮🇯🇰🇳🇵🇷🇷🇷🇸🇸🇹🇺🇺🈁🈂🈚🈯🈳🈵🈶🈷🈸🈹🈺🉐🌀🌂🌃🌄🌅🌆🌇🌈🌊🌙🌟🌴🌵🌷🌸🌹🌺🌻🌾🍀🍁🍂🍃🍅🍆🍉🍊🍎🍓🍔🍘🍙🍚🍛🍜🍝🍞🍟🍡🍢🍣🍦🍧🍰🍱🍲🍳🍴🍵🍶🍸🍺🍻🎀🎁🎂🎃🎄🎅🎆🎇🎈🎉🎌🎍🎎🎏🎐🎑🎒🎓🎡🎢🎤🎥🎦🎧🎨🎩🎫🎬🎯🎰🎱🎵🎶🎷🎸🎺🎾🎿🏀🏁🏃🏄🏆🏈🏊🏠🏢🏣🏥🏦🏧🏨🏩🏪🏫🏬🏭🏯🏰🐍🐎🐑🐒🐔🐗🐘🐙🐚🐛🐟🐠🐤🐦🐧🐨🐫🐬🐭🐮🐯🐰🐱🐳🐴🐵🐶🐷🐸🐹🐺🐻👀👂👃👄👆👇👈👉👊👋👌👍👎👏👐👑👒👔👕👗👘👙👜👟👠👡👢👣👦👧👨👩👫👮👯👱👲👳👴👵👶👷👸👻👼👽👾👿💀💁💂💃💄💅💆💇💈💉💊💋💍💎💏💐💑💒💓💔💗💘💙💚💛💜💝💟💡💢💣💤💦💨💩💪💰💱💱💹💹💺💻💼💽💿📀📖📝📠📡📢📣📩📫📮📱📲📳📴📶📷📺📻📼🔊🔍🔑🔒🔓🔔🔝🔞🔥🔨🔫🔯🔰🔱🔲🔳🔴🕐🕑🕒🕓🕔🕕🕖🕗🕘🕙🕚🕛🗻🗼🗽😁😂😃😄😉😊😌😍😏😒😓😔😖😘😚😜😝😞😠😡😢😣😥😨😪😭😰😱😲😳😷🙅🙆🙇🙌🙏🚀🚃🚄🚅🚇🚉🚌🚏🚑🚒🚓🚕🚗🚙🚚🚢🚤🚥🚧🚬🚭🚲🚶🚹🚺🚻🚼🚽🚾🛀☺✨❕❔✊✌✋☝☀☔☁⛄⚡☕〽♦♣♠⛳⚾⚽➿☎✂⛪⛺⛵⛲♨⚠⛽⃣⃣⃣⃣⃣⃣⃣⬅⬇⬆⃣⃣⃣⃣➡↗↖↘↙◀▶⏪⏩♿✳㊗㊙✴♈♉♊♋♌♍♎⛎♓♒♑♐♏❌⭕]");
  }

  public static String get(String paramString1, String paramString2)
  {
    if (paramString1 == null);
    while (true)
    {
      return paramString2;
      paramString2 = paramString1;
    }
  }

  public static Matcher hashMatcher(String paramString)
  {
    return hashRegexp.matcher(paramString);
  }

  public static boolean isNullOrEmpty(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static Matcher mentionMatcher(String paramString)
  {
    return mentionRegexp.matcher(paramString);
  }

  public static String stripEmoji(String paramString)
  {
    return emojiRegexp.matcher(paramString).replaceAll("");
  }

  public static String stripNewLines(String paramString)
  {
    return paramString.replaceAll("\r\n", "");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.StringUtil
 * JD-Core Version:    0.6.0
 */