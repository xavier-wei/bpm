export default function (size: number, type: string[]) {
  return {
    $validator: (file: File | File[] | null) => {
      if (file) {
        if (Array.isArray(file)) {
          let isValid = true;
          file.forEach(file => {
            if (file.size > size) isValid = false;
            if (!type.includes(file.type)) isValid = false;
          })
          return isValid;
        } else {
          if (file.size > size) return false;
          if (!type.includes(file.type)) return false;
        }
      }
      return true;
    },
    $message: ({ $model }) => {
      if ($model) {
        let convertSize = size / (1024 * 1024) + 'MB';
        if (Array.isArray($model)) {
          let message = '';
          $model.forEach(file => {
            if (file.size > size) message = `單一檔案不可超過${convertSize}`;
            if (!type.includes(file.type)) message = `檔案須為以下格式：${type.join(', ')}`;
          })
          return message;
        } else {
          if ($model.size > size) return `檔案不可超過${convertSize}`;
          if (!type.includes($model.type)) return `檔案須為以下格式：${type.join(', ')}`;
        }
      }
    }
  }
};