function validatePassword(password) {
    // Check if password is at least 8 characters long
    if (password.length < 8) {
      return 'length';
    }
  
    // Check if password contains at least one uppercase letter
    const hasUpperCase = /[A-Z]/.test(password);
    if (!hasUpperCase) {
      return 'upper';
    }
  
    // Check if password contains at least one lowercase letter
    const hasLowerCase = /[a-z]/.test(password);
    if (!hasLowerCase) {
      return 'lower';
    }
  
    // Check if password contains at least one digit
    const hasDigit = /\d/.test(password);
    if (!hasDigit) {
      return 'digit';
    }
  
    // Check if password contains at least one special character
    const hasSpecialCharacter = /[-!@#$%^&*()_+|[\]{};:/<>,.?]/.test(password);
    if (!hasSpecialCharacter) {
      return 'special';
    }
  
    // If password passes all checks, return true
    return 'fine';
  }

function formatDate(created) {
  if(!created){ // if created is null
    return "";
  }

  const date = new Date(created);

    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;
    return formattedDate;
}

function breakLongWord(text, maxLength) {
  const words = text.split(' ');

  for (let i = 0; i < words.length; i++) {
    const word = words[i];

    if (word.length > maxLength && word.indexOf('\n') === -1) {
      const brokenWord = [];
      let currentPosition = 0;

      while (currentPosition < word.length) {
        brokenWord.push(word.slice(currentPosition, currentPosition + maxLength));
        currentPosition += maxLength;
      }

      words[i] = brokenWord.join('\n');
    }
  }

  return words.join(' ');
}


  export  {validatePassword, formatDate, breakLongWord};
