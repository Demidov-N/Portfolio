"""File submission forms"""

from django import forms
from myPipe.validators import validate_file_extension
class UploadFileForm(forms.Form):
    file = forms.ImageField(required=False)
    video = forms.FileField(validators=[validate_file_extension])
