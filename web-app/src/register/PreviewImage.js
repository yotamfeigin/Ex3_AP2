
function PreviewImage({previewImg, setPreviewImg}) {
  
  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64String = reader.result;
        setPreviewImg(base64String);
      };
      reader.readAsDataURL(file);
    }
  };
  

    return (
        <div className="form-group">
            <label htmlFor="p_picture"
                className="btn btn-outline-light btn-sm textColor">
                Choose Picture
            </label>
            <input type="file"
                name="p_picture"
                className="form-control-file"
                id="p_picture"
                hidden
                onChange={handleImageChange}
                 />
            <span>
                <img src={previewImg} alt="Logo" id="profile_picture" />
            </span>
        </div>
    )
}

export default PreviewImage;