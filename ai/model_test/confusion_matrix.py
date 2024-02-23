from sklearn.metrics import confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt

# Load data
df = pd.read_csv("/content/merged_data.csv")  

# Extract labels and texts
labels = df['label'].values
texts = df['transcript'].values

# Create test dataset and DataLoader
test_dataset = CustomDataset(texts, labels, tokenizer, max_len)
test_dataloader = DataLoader(test_dataset, batch_size=2, shuffle=False)


# Perform predictions using the test dataset
model.eval()
test_predictions = []
test_true_labels = []

with torch.no_grad():
    for batch in tqdm(test_dataloader, desc='Testing'):
        input_ids = batch['input_ids'].to(device)
        attention_mask = batch['attention_mask'].to(device)
        labels = batch['labels'].to(device)

        outputs = model(input_ids, attention_mask=attention_mask)
        logits = outputs.logits
        predictions = torch.argmax(logits, dim=1).cpu().numpy()

        test_predictions.extend(predictions)
        test_true_labels.extend(labels.cpu().numpy())

# Create Confusion Matrix
conf_matrix = confusion_matrix(test_true_labels, test_predictions)

# Visualize using Heatmap
plt.figure(figsize=(8, 6))
sns.heatmap(conf_matrix, annot=True, fmt='d', cmap='Blues', xticklabels=['Class 0', 'Class 1', 'Class 2'], yticklabels=['Class 0', 'Class 1', 'Class 2'])
plt.xlabel('Predicted Labels')
plt.ylabel('True Labels')
plt.title('Confusion Matrix')
plt.show()
