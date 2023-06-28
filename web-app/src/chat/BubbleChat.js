import { formatDate, breakLongWord } from "../register/functions";

function BubbleChat({ username, sender, content, created }) {
  const formattedDate = formatDate(created);
  const brokenContent = breakLongWord(content, 20);

  if (sender.username === username) {
    return (
      <>
        <div className="bubble right">{brokenContent}
          <div className="last_seen">{formattedDate}</div>
        </div>
      </>
    );
  } else {
    return (
      <>
        <div className="bubble left">{brokenContent}
          <div className="last_seen">{formattedDate}</div>
        </div>
      </>
    );
  }
}

export default BubbleChat;
