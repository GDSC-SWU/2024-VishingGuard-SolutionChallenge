from transformers import BertTokenizer
import torch
import numpy as np

# Hugging Face의 미리 훈련된 KoBERT 토크나이저를 로드합니다.
tokenizer = BertTokenizer.from_pretrained("monologg/kobert")

max_len = 128  # 미세 조정 중 사용한 max_len과 동일하게 설정합니다.

from transformers import BertForSequenceClassification, BertTokenizer

# 저장된 모델과 토크나이저 불러오기
loaded_model = BertForSequenceClassification.from_pretrained('/content/fine_tuned_model')
loaded_tokenizer = BertTokenizer.from_pretrained('/content/fine_tuned_model')



class BERTDataset(torch.utils.data.Dataset):
    def __init__(self, dataset, sent_idx, label_idx, bert_tokenizer, max_len):
        self.tokenizer = bert_tokenizer
        self.max_len = max_len
        self.sentences = [i[sent_idx] for i in dataset]
        self.labels = [np.int32(i[label_idx]) for i in dataset]

    def __getitem__(self, i):
        sentence, label = self.sentences[i], self.labels[i]
        encoding = self.tokenizer(
            sentence,
            add_special_tokens=True,
            max_length=self.max_len,
            padding='max_length',
            truncation=True,
            return_tensors='pt'
        )
        input_ids = encoding['input_ids'].flatten()
        attention_mask = encoding['attention_mask'].flatten()
        return input_ids, attention_mask, label

    def __len__(self):
        return len(self.labels)

def predict(predict_sentence):
    data = [predict_sentence, '0']
    dataset_another = [data]

    another_test = BERTDataset(dataset_another, 0, 1, tokenizer, max_len)
    test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=1, num_workers=5)

    loaded_model.eval()

    for input_ids, attention_mask, label in test_dataloader:
        with torch.no_grad():
            outputs = loaded_model(input_ids=input_ids, attention_mask=attention_mask)

        logits = outputs.logits
        probabilities = torch.nn.functional.softmax(logits, dim=1).squeeze().numpy()

        # 확률 출력
        print("예측된 확률:", probabilities)

        # 확률에 따라 클래스 분류
        if probabilities[0] > 0.5:
            safety_level = '안전'
        elif probabilities[1] > 0.6:
            safety_level = '위험'
        else:
            safety_level = '경고'

        # 예측된 클래스 출력
        print("예측된 클래스:", safety_level)

        return probabilities, safety_level
