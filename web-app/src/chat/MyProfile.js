function MyProfile({picture, name}) {
    return (
        <div id="user_profile">
        <img src={picture} alt="profile picture" className="chat_profile_picture" />
        <b className="textColor contatcs_chat">{name}</b>
    </div>
    )
}
export default MyProfile;