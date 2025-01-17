package com.joohnq.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.domain.constant.UserFileStorageConstants
import com.joohnq.domain.entity.ImageType
import com.joohnq.shared_resources.Res
import com.joohnq.shared_resources.components.AvatarImage
import com.joohnq.shared_resources.components.ImageCache
import com.joohnq.shared_resources.components.VerticalSpacer
import com.joohnq.shared_resources.greeting
import com.joohnq.shared_resources.theme.Colors
import com.joohnq.shared_resources.theme.Dimens
import com.joohnq.shared_resources.theme.Drawables
import com.joohnq.shared_resources.theme.TextStyles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    userName: String,
    images: List<DrawableResource>,
    image: String?,
    imageType: ImageType,
    date: String,
) {
    Column(
        modifier = Modifier.fillMaxSize().background(
            color = Colors.Brown80, shape = Dimens.Shape.BottomLarge
        ).padding(20.dp).then(modifier)
    ) {
        Row {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Drawables.Icons.Calendar),
                    modifier = Modifier.size(20.dp),
                    tint = Colors.Brown40,
                    contentDescription = null
                )
                Text(text = date, style = TextStyles.TextSmBold(), color = Colors.White)
            }
        }
        VerticalSpacer(15.dp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                when (imageType) {
                    ImageType.DEVICE -> {
                        ImageCache(
                            directory = UserFileStorageConstants.AVATAR_DIRECTORY,
                            fileName = UserFileStorageConstants.AVATAR_FILE_NAME
                        )
                    }

                    ImageType.DRAWABLE -> {
                        AvatarImage(
                            painterResource(images[it.toInt()])
                        )
                    }
                }
            }
            Text(
                text = stringResource(Res.string.greeting, userName),
                style = TextStyles.HeadingSmExtraBold(),
                color = Colors.White
            )
        }
    }
}